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
SRC_URI[md5sum] = "4ea6fd64603536406166600bcc296fc8"
SRC_URI[sha256sum] = "ca5218fade0204d59947126c38439f432853543b0818d9d728c589dfe7f3a421"

PR = "r1"

FILES_${PN}-dev += " \
    /usr/lib/graphviz/*.so \
"

inherit autotools-brokensep pkgconfig
