SUMMARY = "A transparent persistent cache for the requests library"
HOMEPAGE = "https://github.com/reclosedev/requests-cache"
AUTHOR = "Roman Haritonov <>"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=ee449ea149592ffd5b15650216fc33a1"

inherit setuptools3

SRC_URI = "https://files.pythonhosted.org/packages/12/c2/cbdf169cb621aeb8b0f1097e357b65e018a30079501fc88c9f889ad61b00/requests-cache-0.8.1.tar.gz"
SRC_URI[md5sum] = "8c2714761fdd6ff596edcad78ad3ecb1"
SRC_URI[sha256sum] = "27d3eb276ab3affa9864dfc0475241d6d960dd566d57ec46ffa7759c2c74ed1c"

S = "${WORKDIR}/requests-cache-0.8.1"

RDEPENDS:${PN} = "python3-requests python3-urllib3 python3-appdirs python3-attrs python3-cattrs python3-url-normalize"

include python3-package-split.inc
