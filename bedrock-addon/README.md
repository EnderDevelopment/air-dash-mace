# Dash Mace (Bedrock Edition Addon)

This folder is a **Bedrock Edition addon** that recreates the dash mace from the Java
plugin, because Java Edition plugins and Bedrock Edition addons are two completely
different formats and **cannot be converted or exported** from one to the other.

## What this is

- A `manifest.json` behavior + script pack.
- A custom item `dashmace:dash_mace`.
- A Script API (`scripts/main.js`) that, when you right-click / tap while holding the
  mace, hurls you in the direction you are looking (a "dash").
- A 3 second cooldown after each dash.
- An item texture.

## How to install it in Bedrock Edition

The Script API requires **Minecraft Bedrock version 1.20.10 or newer**, and "Beta
APIs" (Holiday Creator Features) must be enabled on the world.

1. On a PC, open your Bedrock `resource_packs` and `behavior_packs` folders, or use
   the "Import" flow in the Marketplace / `.mcaddon`.
2. To make a single importable `.mcaddon`, zip the contents of this folder into a
   `.mcaddon` file (it will be treated as a world template / addon).
3. Enable the "Dash Mace" pack in your world's **Behavior Packs** settings, and turn on
   **Beta APIs** for the world.
4. Give yourself the item with the command:
   `/give @s dashmace:dash_mace`

## Files in this folder

- `manifest.json` – pack manifest (behavior + script modules).
- `items/dash_mace.json` – the custom item definition.
- `scripts/main.js` – dash behavior and cooldown logic.
- `textures/item_texture.json` – maps the item texture.
- `textures/items/dash_mace.svg` – the mace icon.

## Limitations

- The dash only works while the world has Script API (Beta APIs) enabled.
- This addon is a separate re-implementation, not a port of the Java plugin code —
  Java plugins cannot run on Bedrock.
