#!/usr/bin/env python3
"""Item helper: given an item slug, prints everything needed to register it."""
import json, re, sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]

with open(ROOT / "items_data.json") as f:
    DATA = json.load(f)

# Build ID -> slug map from all items + known overrides
def to_slug(name):
    s = name.lower().replace("'s", "_s").replace("'", "").replace("-", "_")
    s = s.replace(",", "").replace(".", "")
    s = re.sub(r'[^a-z0-9_ ]', '', s)
    s = s.replace(" ", "_")
    s = re.sub(r'_+', '_', s).strip('_')
    return s

ID_TO_SLUG = {}
for item_id, item in DATA.items():
    ID_TO_SLUG[item_id] = to_slug(item["itemname"])

# Also slug -> data for lookup
SLUG_TO_DATA = {}
for item_id, item in DATA.items():
    slug = to_slug(item["itemname"])
    SLUG_TO_DATA[slug] = {**item, "id": item_id}

def find_by_name(name):
    """Find item data by name or slug."""
    slug = to_slug(name)
    # Try slug first
    if slug in SLUG_TO_DATA:
        return slug, SLUG_TO_DATA[slug]
    # Try partial match
    for s, d in SLUG_TO_DATA.items():
        if slug in s or s in slug:
            return s, d
    return None, None

def fmt_stat_value(v):
    if isinstance(v, float) and v < 1:
        # Move speed etc - keep as float literal
        return f"{v}f"
    return f"{int(v)}f" if v == int(v) else f"{v}f"

def main():
    if len(sys.argv) < 2:
        print("Usage: python item_helper.py <item_slug_or_name>")
        print("Example: python item_helper.py abyssal_mask")
        sys.exit(1)

    query = " ".join(sys.argv[1:])
    slug, data = find_by_name(query)

    if not data:
        print(f"Item '{query}' not found.")
        # Show first 10 matches for debugging
        matches = [s for s in SLUG_TO_DATA if query.lower() in s][:10]
        if matches:
            print(f"Did you mean: {', '.join(matches)}")
        sys.exit(1)

    item_id = data["id"]
    name = data["itemname"]
    depth = data["depth"]
    into = data.get("into", [])
    from_ids = data.get("from", [])
    stats = data.get("stats", {})

    # Classify
    is_full = depth == 3 and not into
    is_component = not is_full
    item_class = "FullItem" if is_full else "ComponentItem"

    print(f"=== {name} (id={item_id}, slug={slug}) ===")
    print(f"Class: {item_class}")
    print(f"Depth: {depth}, into={into}, from={from_ids}")
    print()

    # Ingredient slugs
    ingredient_slugs = []
    for fid in from_ids:
        islug = ID_TO_SLUG.get(fid)
        if islug:
            ingredient_slugs.append(islug)
            iname = DATA[fid]["itemname"]
            print(f"Ingredient {fid}: {iname} -> league-of-crafters:{islug}")
        else:
            print(f"Ingredient {fid}: UNKNOWN ID")
            ingredient_slugs.append(f"UNKNOWN_{fid}")

    print()

    # ModItems.java registration line
    stat_entries = [f'"{k}", {fmt_stat_value(v)}' for k, v in stats.items()]
    stats_str = ", ".join(stat_entries)
    if stats_str:
        reg_line = f'registerCustomItem("{slug}", new {item_class}(new Item.Properties(), Map.of({stats_str})));'
    else:
        reg_line = f'registerCustomItem("{slug}", new {item_class}(new Item.Properties()));'

    print("=== ModItems.java ===")
    print(f"// Replace: registerItem(\"{slug}\");")
    print(f"    public static final Item {slug.upper()} = {reg_line}")
    print()

    # Recipe JSON (shapeless)
    if ingredient_slugs:
        print("=== Recipe JSON (shapeless) ===")
        recipe = {
            "type": "minecraft:crafting_shapeless",
            "category": "misc",
            "ingredients": [{"item": "league-of-crafters:" + isl} for isl in ingredient_slugs],
            "result": {"id": f"league-of-crafters:{slug}", "count": 1}
        }
        print(json.dumps(recipe, indent=2))
    else:
        print("(No recipe — depth=0 starter item with no from[] ingredients)")

    print()

    # Patchouli entry JSON
    print("=== Patchouli Entry JSON ===")
    pages = [{"type": "patchouli:text", "text": f"entry.league-of-crafters.item_{slug}.desc"}]
    if ingredient_slugs:
        pages.append({"type": "patchouli:crafting", "recipe": f"league-of-crafters:{slug}"})
    entry = {
        "name": f"item.league-of-crafters.{slug}",
        "category": "league-of-crafters:items",
        "icon": f"league-of-crafters:{slug}",
        "pages": pages
    }
    print(json.dumps(entry, indent=2))

if __name__ == "__main__":
    main()
