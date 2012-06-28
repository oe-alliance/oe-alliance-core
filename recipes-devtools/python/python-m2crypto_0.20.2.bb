DESCRIPTION = "A Python crypto and SSL toolkit"
SECTION = "devel/python"
HOMEPAGE = "http://chandlerproject.org/bin/view/Projects/MeTooCrypto"
PRIORITY = "optional"
DEPENDS = "openssl swig-native"
LICENSE = "BSD"
SRCNAME = "M2Crypto"
PR = "ml0"

SRC_URI = "\
  http://pypi.python.org/packages/source/M/${SRCNAME}/${SRCNAME}-${PV}.tar.gz \
  file://install.patch \
  file://m2crypto-0.20.2-openssl-1.0.0.patch;striplevel=0 \
"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit setuptools

export STAGING_DIR
export STAGING_INCDIR
export STAGING_LIBDIR

SRC_URI[md5sum] = "6c24410410d6eb1920ea43f77a93613a"
SRC_URI[sha256sum] = "fc66b96ad2a14de1a502358286d3490677eda30385645fca995fffff22b73e6e"

NATIVE_INSTALL_WORKS = "1"
BBCLASSEXTEND = "native"
