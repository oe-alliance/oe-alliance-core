DESCRIPTION = "Transmission RPC is a python module that can communicate with the bittorrent client Transmission through json-rpc"
HOMEPAGE = "http://bitbucket.org/blueluna/transmissionrpc/wiki/Home"
SECTION = "devel/python"
PRIORITY = "optional"
LICENSE = "MIT"
RDEPENDS_${PN} = "python-simplejson"
PV = "0.7+hg${SRCREV}"

SRC_URI = "hg://bitbucket.org/blueluna;proto=http;module=transmissionrpc;rev=${SRCREV}"

SRCREV = "4fe71eb818d0"
S = "${WORKDIR}/transmissionrpc"

inherit setuptools
