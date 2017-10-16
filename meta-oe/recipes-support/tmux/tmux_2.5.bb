LICENSE = "ISC"
LIC_FILES_CHKSUM = "file://COPYING;md5=f7d9aab84ec6567139a4755c48d147fb"
DEPENDS = "ncurses libevent"

SRC_URI = "https://github.com/tmux/tmux/releases/download/2.5/tmux-2.5.tar.gz"

SRC_URI[md5sum] = "4a5d73d96d8f11b0bdf9b6f15ab76d15"
SRC_URI[sha256sum] = "ae135ec37c1bf6b7750a84e3a35e93d91033a806943e034521c8af51b12d95df"

SUMMARY = "tmux is a software application that can be used to multiplex several virtual consoles"
HOMEPAGE = "https://github.com/tmux/tmux"
SECTION = "console"

inherit autotools pkgconfig

EXTRA_OECONF = ""
CONFFILES_${PN} += "${sysconfdir}/tmux.conf"

do_configure() {
    :
    autotools_do_configure
    test -d ${WORKDIR}/build/compat || mkdir -p ${WORKDIR}/build/compat
}

do_install_append() {
    install -d ${D}${sysconfdir}
    install -m 644 ${S}/example_tmux.conf ${D}${sysconfdir}/tmux.conf
}

