PV="4.9.15"
HOMEPAGE = "https://www.samba.org/"
SECTION = "console/network"

LICENSE = "GPL-3.0+ & LGPL-3.0+ & GPL-2.0+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://${COREBASE}/meta/files/common-licenses/LGPL-3.0;md5=bfccfe952269fff2b407dd11f2f3083b \
                    file://${COREBASE}/meta/files/common-licenses/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6 "

SAMBA_MIRROR = "http://samba.org/samba/ftp"
MIRRORS += "\
${SAMBA_MIRROR}    http://mirror.internode.on.net/pub/samba \n \
${SAMBA_MIRROR}    http://www.mirrorservice.org/sites/ftp.samba.org \n \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/samba:"

SRC_URI = "${SAMBA_MIRROR}/stable/samba-${PV}.tar.gz \
           file://001-samba-4.9.15-pam.patch \
           file://002-dnsserver-4.9.15.patch \
           file://010-source3-msgsock-nvram-fix.patch \
           file://011-source4-msgsock-nvram-fix.patch \
           file://101-do-not-check-xsltproc-manpages.patch \
           file://102-samba-4.9.15-unbundle-libbsd.patch \
           file://103-tmsize-overflow-fix.patch \
           file://201-add-config-option-without-valgrind.patch \
           file://202-iconv-4.9.15.patch \
           file://203-netdb_defines.patch \
           file://206-lib-replace-wscript-Avoid-generating-nested-main-fun.patch \
           file://207-glibc_only.patch \
           file://208-talloc_old_kernel_fix.patch \
           file://210-skip-faulty-ldb_match_test.patch \
           file://300-Revert-pam_smbpass-REMOVE-this-PAM-module.patch \
           file://301-Revert-source3-wscript-remove-pam_smbpass-option-as-it-was-removed.patch \
           file://302-dynamically-create-a-samba-account-if-needed.patch \
           file://heimdal_build_fix.patch \
           file://smb.conf \
           file://smb-user.conf \
           file://smb-branding.conf \
           file://smb-global.conf \
           file://smb-shares.conf \
           file://smb-vmc.samba \
           file://init.samba \
           file://pam.samba \
           file://users.map \
           file://smbpasswd \
           file://volatiles.03_samba \
           "
SRC_URI_append_libc-musl = " \
           file://400-samba-4.10.10-remove-getpwent_r.patch \
           file://401-cmocka-uintptr_t.patch \
           file://402-samba-fix-musl-lib-without-innetgr.patch \
          "

SRC_URI_append_sh4 = " \
           file://209-strtoull.patch \
          "

SRC_URI[md5sum] = "57510c96714fa8ad0dc9f25fa458c7d1"
SRC_URI[sha256sum] = "377102b80b97941bf0d131b828cae8415190e5bdd2928c2e2c954e29f1904496"

UPSTREAM_CHECK_REGEX = "samba\-(?P<pver>4\.9(\.\d+)+).tar.gz"
