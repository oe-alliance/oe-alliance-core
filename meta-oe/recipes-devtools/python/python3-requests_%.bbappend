RDEPENDS:${PN}:append = " python3-charset-normalizer"

python3-charset-normalizer

include python3-package-split.inc

SRC_URI[sha256sum] = "18817f8c57c6263968bc123d237e3b8b08ac046f5456bd1e307ee8f4250d3517"

SRC_URI:remove = "file://CVE-2026-25645.patch"

PV = "2.33.1"
