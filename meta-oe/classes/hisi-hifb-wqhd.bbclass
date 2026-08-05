HISI_HIFB_WQHD_PATCH ?= "${@bb.utils.contains('MACHINE_FEATURES', 'e2egl', '1', '0', d)}"
HISI_HIFB_WQHD_VRAM0_SIZE ?= "43200"

hisi_hifb_wqhd_patch() {
    if [ "${HISI_HIFB_WQHD_PATCH}" != "1" ]; then
        return 0
    fi

    ko="$1"
    load="$2"
    if [ ! -e "$ko" ]; then
        return 0
    fi

    python3 - "$ko" "$load" "${HISI_HIFB_WQHD_VRAM0_SIZE}" <<'PY'
import pathlib
import re
import sys

ko = pathlib.Path(sys.argv[1])
load = pathlib.Path(sys.argv[2])
vram0_size = sys.argv[3]

patterns = (
    (
        "hifb_set_par adjacent width/height limit",
        bytes.fromhex("383400e31e0d52e3"),
        bytes.fromhex("a03500e30a0c52e3"),
    ),
    (
        "hifb_set_par split width/height limit",
        bytes.fromhex("1e0d52e33e0000ca041091e5383400e3"),
        bytes.fromhex("0a0c52e33e0000ca041091e5a03500e3"),
    ),
    (
        "hifb_disp_setdispsize width/height limit",
        bytes.fromhex("383400e3030058e11e0d5793"),
        bytes.fromhex("a03500e3030058e10a0c5793"),
    ),
)

data = bytearray(ko.read_bytes())

changed = False
matched = False
for name, old, new in patterns:
    old_count = data.count(old)
    new_count = data.count(new)

    if old_count == 1:
        offset = data.find(old)
        data[offset:offset + len(old)] = new
        changed = True
        matched = True
        print(f"NOTE: hi_fb.ko WQHD patch applied to {ko}: {name} at 0x{offset:x}")
        continue

    if old_count == 0 and new_count == 1:
        matched = True
        continue

    if old_count > 1 or new_count > 1:
        print(f"WARNING: hi_fb.ko WQHD patch skipped for {ko}: ambiguous {name} signature", file=sys.stderr)
        sys.exit(0)

if not matched:
    print(f"WARNING: hi_fb.ko WQHD patch skipped for {ko}: unknown binary signature", file=sys.stderr)
    sys.exit(0)

if changed:
    ko.write_bytes(data)

if load.exists():
    text = load.read_text()
    text, replacements = re.subn(
        r'(?m)^insmod hi_fb\.ko(?:\s+video="[^"]*")?\s*$',
        f'insmod hi_fb.ko video="hi_fb:vram0_size:{vram0_size}"',
        text,
        count=1,
    )
    if replacements != 1:
        print(f"WARNING: hi_fb.ko WQHD patch did not find active insmod line in {load}", file=sys.stderr)
    load.write_text(text)
PY
}
