inherit upx-compress

SRC_URI:append = " \
           file://mke2fs_conf.patch \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PR .= ".0"

EXTRA_OECONF += "--enable-defrag"

do_install:append() {
	if [ -e ${D}${base_sbindir}/e4defrag ]; then
		install -d ${D}${sbindir}
		mv ${D}${base_sbindir}/e4defrag ${D}${sbindir}/e4defrag
	fi
}

PACKAGES =+ "e2fsprogs-e4defrag"
FILES:e2fsprogs-e4defrag = "/usr/sbin/e4defrag"

PACKAGE_NO_LOCALE = "1"
