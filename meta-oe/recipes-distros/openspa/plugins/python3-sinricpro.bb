DESCRIPTION = "sinric pro client for Alexa"
SECTION = "devel/python"
MAINTAINER = "Sinric Pro"
HOMEPAGE = "https://sinric.pro"
LICENSE = "GPL-2.0-or-later"
require conf/license/license-gplv2.inc

DEPENDS += "python3-wheel-native"
RDEPENDS:${PN} = "python3-websockets python3-asyncio python3-aiohttp"

PV = "5.2.1"

inherit python3-dir gitpkgv python_setuptools_build_meta

SRCREV = "6527a1864e908e801a69d315069ec1b5ea3fb3b2"

SRC_URI = "git://github.com/sinricpro/python-sdk;protocol=https;branch=master \
          file://fix_adjust_tv_volume.patch \
          "
