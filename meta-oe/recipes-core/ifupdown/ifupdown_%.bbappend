PR="r1"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI_remove = "file://inet-6-.defn-fix-inverted-checks-for-loopback.patch"
SRC_URI += " \
            file://inet.defn \
            file://inet6.defn \
           "

# Inject oe-a/yocto specific definitions for DHCP clients (e.g. odhcp6c for IPv6)
do_fixdhcp() {
	cp ${WORKDIR}/inet.defn ${S}
	cp ${WORKDIR}/inet6.defn ${S}
}

do_install_append() {
	ln ${D}${base_sbindir}/ifup ${D}${base_sbindir}/ifquery
	cd ${D}${mandir}/man8 && ln -s ifup.8 ifquery.8
}

addtask fixdhcp after do_unpack before do_patch
