RDEPENDS:${PN} += "libusb1"

# setuptools_scm is already pulled in via DEPENDS; drop setup.py's
# setup_requires call which triggers a setuptools/Python 3.14 metadata crash.
do_configure:prepend() {
    sed -i "/setup_requires=/d" ${S}/setup.py
}

include python3-package-split.inc
