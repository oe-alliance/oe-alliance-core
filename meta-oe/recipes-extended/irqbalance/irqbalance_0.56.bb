SUMMARY = "Daemon to balance interrupts for SMP systems"
HOMEPAGE = "http://irqbalance.org/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "glib-2.0"
PR = "r1"

SRC_URI = "http://irqbalance.org/releases/${BP}.tar.bz2"
SRC_URI[md5sum] = "cd0c4d3b2bb84778a04fc594ad83949a"
SRC_URI[sha256sum] = "516e1bdc5469f0f350e2e9ad2925b221faa4497a608c8d4e28a997938451c5fc"

inherit autotools
