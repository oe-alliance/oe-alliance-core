hisi_keys_power_only_patch() {
    ko="$1"
    if [ ! -e "$ko" ]; then
        return 0
    fi

    python3 - "$ko" <<'PY'
import pathlib
import sys

ko = pathlib.Path(sys.argv[1])
data = bytearray(ko.read_bytes())

# The proprietary driver reports KEY_UNKNOWN for every scancode that is not in
# its sparse keymap.  Terminate the keymap after KEY_POWER and make the polling
# thread discard unknown scancodes instead of passing them to the input layer.
code_patterns = (
    (
        "4.4.35 compact polling loop",
        bytes.fromhex("000050e3b820d011f020a003000054e3"),
        bytes.fromhex("000050e3b820d0110300000a000054e3"),
    ),
    (
        "4.4.35 polling loop with debug output",
        bytes.fromhex("000050e30410a0e1b850d011f050a003"),
        bytes.fromhex("000050e30410a0e1b850d0110800000a"),
    ),
    (
        "4.4.176 polling loop with debug output",
        bytes.fromhex("000050e3b850d011f050a00384009fe5"),
        bytes.fromhex("000050e3b850d0110700000a84009fe5"),
    ),
)

power_map = bytes.fromhex("010000005700000074000000")
map_old = power_map + bytes.fromhex("01000000")
map_new = power_map + bytes.fromhex("00000000")

matches = []
for name, old, new in code_patterns:
    old_count = data.count(old)
    new_count = data.count(new)
    if old_count > 1 or new_count > 1:
        print(f"WARNING: hisi_keys.ko power-only patch skipped for {ko}: ambiguous {name} signature", file=sys.stderr)
        sys.exit(0)
    if old_count == 1:
        matches.append((name, "old", old, new))
    if new_count == 1:
        matches.append((name, "new", old, new))

map_old_count = data.count(map_old)
map_new_count = data.count(map_new)
if len(matches) != 1 or map_old_count > 1 or map_new_count > 1 or map_old_count + map_new_count != 1:
    print(f"WARNING: hisi_keys.ko power-only patch skipped for {ko}: unknown binary signature", file=sys.stderr)
    sys.exit(0)

name, code_state, code_old, code_new = matches[0]
map_state = "old" if map_old_count == 1 else "new"
if code_state != map_state:
    print(f"WARNING: hisi_keys.ko power-only patch skipped for {ko}: inconsistent patch state", file=sys.stderr)
    sys.exit(0)

if code_state == "old":
    code_offset = data.find(code_old)
    map_offset = data.find(map_old)
    data[code_offset:code_offset + len(code_old)] = code_new
    data[map_offset:map_offset + len(map_old)] = map_new
    ko.write_bytes(data)
    print(f"NOTE: hisi_keys.ko power-only patch applied to {ko}: {name}")
PY
}
