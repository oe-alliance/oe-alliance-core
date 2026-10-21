RDEPENDS:${PN} += "libusb1"

# setuptools_scm is already pulled in via DEPENDS. Drop setup_requires from
# both setup files to avoid the setuptools/Python 3.14 metadata crash.
do_configure:prepend() {
    sed -i "/^[[:space:]]*setup_requires[[:space:]]*=/d" ${S}/setup.py ${S}/setup.cfg
}

include python3-package-split.inc
