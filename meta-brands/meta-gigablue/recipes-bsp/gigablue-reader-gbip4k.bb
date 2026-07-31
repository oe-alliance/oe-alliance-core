SUMMARY = "libreader for Gigablue Model ${MACHINEBUILD}"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINEBUILD}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(gbmv200)$"

SRCDATE = "20221220"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/gigablue/mv200/${MACHINEBUILD}-libreader-${SRCDATE}.tar.gz"

S = "${UNPACKDIR}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${S}/libreader ${D}/${bindir}
}

do_install:append() {
    python3 - <<'PY'
from pathlib import Path

reader = Path("${D}/${bindir}/libreader")

old = bytes.fromhex("0a30c5e5")
new = bytes.fromhex("0a20c5e5")

if reader.exists():
    data = reader.read_bytes()
    if old in data:
        reader.write_bytes(data.replace(old, new, 1))
PY
}

do_package_qa() {
}

FILES:${PN}  = "${bindir}/libreader"

SRC_URI[md5sum] = "a294e4449a156499731fec29ad7e0ef8"
SRC_URI[sha256sum] = "834bc85e7409ae1d24800d1c1762b581cddea47d2c4429a4feab108adf107b9a"

INSANE_SKIP:${PN} += "already-stripped"
