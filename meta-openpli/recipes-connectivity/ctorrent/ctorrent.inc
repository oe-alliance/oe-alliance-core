DESCRIPTION = "CTorrent is a  console BitTorrent client \
  with a focus on being lightweight and quick."
HOMEPAGE = "http://www.rahul.net/dholmes/ctorrent/"
SECTION = "network"
LICENSE = "GPLv2+"
DEPENDS = "openssl"

SRC_URI = "${SOURCEFORGE_MIRROR}/ctorrent/ctorrent-${PV}.tar.bz2"

inherit autotools
