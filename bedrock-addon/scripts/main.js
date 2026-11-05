import { world, system, EquipmentSlot, GameMode } from "@minecraft/server";

const DASH_ITEM = "dashmace:dash_mace";
const DASH_SPEED = 1.8;
const DASH_DURATION = 6; // ticks (about 0.3 seconds)
const COOLDOWN_TICKS = 60; // 3 seconds

// Stores last dash tick per player id to enforce cooldown
const lastDashTicks = new Map();

function isHoldingDashMace(player) {
  const equip = player.getComponent("minecraft:equippable");
  if (!equip) return false;
  const item = equip.getEquipment(EquipmentSlot.Mainhand);
  return item && item.typeId === DASH_ITEM;
}

world.beforeEvents.itemUse.subscribe((event) => {
  const { item, source } = event;
  if (!item || item.typeId !== DASH_ITEM) return;

  const player = source;
  if (!player || player.typeId !== "minecraft:player") return;

  // Allow using from ground too but only dash when airborne
  const currentTick = system.currentTick;

  // Cooldown check
  const last = lastDashTicks.get(player.id) ?? -COOLDOWN_TICKS;
  if (currentTick - last < COOLDOWN_TICKS) {
    const remaining = Math.ceil((COOLDOWN_TICKS - (currentTick - last)) / 20);
    player.sendMessage(`§cDash on cooldown! Wait ${remaining}s.`);
    event.cancel = true;
    return;
  }

  lastDashTicks.set(player.id, currentTick);

  // Apply dash impulse in the direction the player is looking
  const rotation = player.getRotation();
  const pitch = (rotation.x * Math.PI) / 180;
  const yaw = (rotation.y * Math.PI) / 180;

  const x = -Math.sin(yaw) * Math.cos(pitch);
  const y = -Math.sin(pitch);
  const z = Math.cos(yaw) * Math.cos(pitch);

  const vel = player.getVelocity();
  player.setVelocity({
    x: vel.x + x * DASH_SPEED,
    y: vel.y + y * DASH_SPEED,
    z: vel.z + z * DASH_SPEED,
  });

  player.playSound("mob.enderdragon.flap", { volume: 0.6, pitch: 1.5 });
  player.sendMessage("§aWhoosh!");
});
