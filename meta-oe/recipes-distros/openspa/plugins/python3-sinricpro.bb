DESCRIPTION = "sinric pro client for Alexa"
SECTION = "devel/python"
MAINTAINER = "Sinric Pro"
HOMEPAGE = "https://sinric.pro"
LICENSE = "GPLv2+"
require conf/license/license-gplv2.inc

RDEPENDS:${PN} = "${PYTHON_PN}-websockets ${PYTHON_PN}-loguru ${PYTHON_PN}-asyncio"

PV = "2.6.4"

inherit ${PYTHON_PN}-dir gitpkgv setuptools3

SRCREV = "1f5eb31e72cb63b5c861170aa8dee488212cc84b"

SRC_URI = "git://github.com/sinricpro/python-sdk;protocol=https;branch=master \
          file://append_change_channel_by_number.patch \
          file://set_log_folder_to_tmp.patch"

S = "${WORKDIR}/git"

