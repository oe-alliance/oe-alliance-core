SECTION = "graphical"
SUMMARY = "Graphviz - Graph Visualization Software"
HOMEPAGE = "http://www.graphviz.org"
LICENSE = "EPL-1.0"
DEPENDS = "cairo pango expat intltool-native gtk-doc gettext-native python libtool"

SRC_URI = " \
    http://www.graphviz.org/pub/graphviz/stable/SOURCES/graphviz-${PV}.tar.gz \
    file://cross_compile.patch \
"
LIC_FILES_CHKSUM = "file://COPYING;md5=9109f5fc16cf963fb3cdd32781b3ce04"
SRC_URI[md5sum] = "5b6a829b2ac94efcd5fa3c223ed6d3ae"
SRC_URI[sha256sum] = "81aa238d9d4a010afa73a9d2a704fc3221c731e1e06577c2ab3496bdef67859e"

PR = "r1"

FILES_${PN}-dev += " \
    /usr/lib/graphviz/*.so \
"

inherit autotools-brokensep pkgconfig
