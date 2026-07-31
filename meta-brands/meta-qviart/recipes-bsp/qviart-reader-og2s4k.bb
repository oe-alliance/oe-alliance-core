SUMMARY = "libreader for Qviart Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(og2s4k)$"

SRCDATE = "20230710"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/qviart/${MACHINE}-libreader-${SRCDATE}.tar.gz"

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

SRC_URI[md5sum] = "4aab94c84657f45fed1f3702658d4e93"
SRC_URI[sha256sum] = "a3a6927a555cc221595fc333d28f4c418d8b419bdac99ee5658214f1ccd50430"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped"
