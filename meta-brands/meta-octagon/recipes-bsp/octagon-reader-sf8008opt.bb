SUMMARY = "libreader for Octagon Model ${MACHINE}"
SECTION = "base"
PRIORITY = "optional"
require conf/license/license-close.inc
PACKAGE_ARCH = "${MACHINE_ARCH}"

RDEPENDS:${PN} = "libsdl"

COMPATIBLE_MACHINE = "^(sf8008opt)$"

SRCDATE = "20210831"

PV = "${SRCDATE}"
PR = "r0"

SRC_URI = "https://source.mynonpublic.com/octagon/${MACHINE}-libreader-${SRCDATE}.tar.gz"

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

SRC_URI[md5sum] = "5ced8d36df6d90a89840865237e205bd"
SRC_URI[sha256sum] = "ef3433d8c2c513b4540db3663cd409f53a3241aa0498551bc0eadd8c6c36b509"

INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_PACKAGE_DEBUG_SPLIT = "1"
INSANE_SKIP:${PN} += "already-stripped"
