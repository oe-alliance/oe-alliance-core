#
# base recipe: http://cgit.openembedded.org/cgit.cgi/openembedded-core/tree/meta/\
#	       recipes-devtools/insserv/insserv_1.14.0.bb?h=fido
# base branch: fido
# base commit: f0873b83d693af4a103999160d67fcf25c7eedc1
#

SUMMARY = "Boot sequence organizer using LSB init.d dependencies"
DESCRIPTION = "This utility reorders the init.d boot scripts based on \
dependencies agiven in scripts'LSB comment headers, or in override files \
included in this package or added in /etc/insserv."
# There is no known home page for insserv

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

PR = "r0"
inherit debian-package
PV = "1.14.0"

#Add option -D-GNU_SOURCE: The O_NOATIME variable in insserv.c \
#is depends on __USE_GNU and __USE_GNU depends on _GNU_SOURCE
CFLAGS += " -D_GNU_SOURCE"

INSTDIR = "${D}"
INSTDIR_class-native = "${D}${STAGING_DIR_NATIVE}"

FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += "file://170_undeclared_extension.patch \
            file://180_MAXSYMLINKS.patch \
            file://rpcbind \
            file://insserv.conf \
           "

do_install () {
	oe_runmake 'DESTDIR=${INSTDIR}' install

	install -m0644 ${S}/insserv.conf ${D}${sysconfdir}/insserv.conf
	
	install -d ${D}${sysconfdir}/insserv/overrides
	install -d ${D}${sysconfdir}/insserv.conf.d
	install -m0644 ${WORKDIR}/rpcbind ${D}${sysconfdir}/insserv.conf.d/rpcbind
	install -m0644 ${WORKDIR}/insserv.conf ${D}${sysconfdir}/insserv.conf

	#Create /etc/bash_completion.d folder
	install -d ${D}${sysconfdir}/bash_completion.d
	chmod 0755 ${D}${sysconfdir}/bash_completion.d

	#Install /etc/bash_completion.d/insserv
	install -m 0644 ${S}/debian/insserv.bash-completion \
			${D}${sysconfdir}/bash_completion.d/insserv
}

#do_install () {
#        oe_runmake 'DESTDIR=${D}' install
#        install -m0644 ${WORKDIR}/insserv.conf ${D}${sysconfdir}/insserv.conf
#}
#
#do_install_class-native () {
#    oe_runmake 'DESTDIR=${D}/${STAGING_DIR_NATIVE}' install
#}

BBCLASSEXTEND = "native"
