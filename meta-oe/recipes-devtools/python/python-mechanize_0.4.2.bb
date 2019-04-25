SUMMARY = "Stateful programmatic web browsing, after Andy Lester's Perl module WWW::Mechanize."
HOMEPAGE = "https://github.com/python-mechanize/mechanize"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=621053d4e9daec9454e15e60fe40214f"

RDEPENDS_${PN} = "python-core python-robotparser"

SRC_URI = "https://files.pythonhosted.org/packages/79/6b/c256ffe2abd560a2857bd66131e01ddfb4b123510a0100a495ded8f191cc/mechanize-${PV}.tar.gz"

SRC_URI[md5sum] = "4419ed4dafb78a3a50d898f48b56407b"
SRC_URI[sha256sum] = "b680ca1b4fabe5ef52024d120f40b8e2ed7d175ed4d67225d2c477dac7c7a58b"

S = "${WORKDIR}/mechanize-${PV}"

inherit setuptools

FILES_${PN}-src_append = " \
    ${libdir}/${PYTHON_DIR}/site-packages/mechanize-0.4.2-py2.7.egg-info/* \
"

include python-package-split.inc
