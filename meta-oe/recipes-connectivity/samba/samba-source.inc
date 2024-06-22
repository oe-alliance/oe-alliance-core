PV = "4.19.7"
HOMEPAGE = "https://www.samba.org/"
SECTION = "console/network"

LICENSE = "GPL-3.0-or-later & LGPL-3.0-or-later & GPL-2.0-or-later"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504 \
                    file://${COREBASE}/meta/files/common-licenses/LGPL-3.0-or-later;md5=c51d3eef3be114124d11349ca0d7e117 \
                    file://${COREBASE}/meta/files/common-licenses/GPL-2.0-or-later;md5=fed54355545ffd980b814dab4a3b312c"

SAMBA_MIRROR = "http://samba.org/samba/ftp"
MIRRORS += "\
${SAMBA_MIRROR}    http://mirror.internode.on.net/pub/samba \n \
${SAMBA_MIRROR}    http://www.mirrorservice.org/sites/ftp.samba.org \n \
"

export PYTHONHASHSEED="1"

FILESEXTRAPATHS:prepend := "${THISDIR}/samba:"
SRC_URI = "${SAMBA_MIRROR}/stable/samba-${PV}.tar.gz \
           file://001-samba-4.14-pam.patch \
           file://010-source3-msgsock-nvram-fix.patch \
           file://011-source4-msgsock-nvram-fix.patch \
           file://101-do-not-check-xsltproc-manpages.patch \
           file://102-add-options-to-configure-the-use-of-libbsd.patch \
           file://103-tmsize-overflow-fix.patch \
           file://104-samba-defeat-iconv-test.patch \
           file://106-source3-wscript-disable-check-fcntl-F_OWNER_EX.patch \
           file://107-source3-wscript-disable-check-fcntl-RW_HINTS.patch \
           file://108-do-not-import-target-module-while-cross-compile.patch \
           file://109-Fix-pyext_PATTERN-for-cross-compilation.patch \
           file://201-add-config-option-without-valgrind.patch \
           file://202-samba-4.14-unbundle-icu.patch \
           file://207-glibc_only.patch \
           file://208-talloc_old_kernel_fix.patch \
           file://300-Revert-pam_smbpass-REMOVE-this-PAM-module.patch \
           file://301-Revert-source3-wscript-remove-pam_smbpass-option-as-it-was-removed.patch \
           file://302-dynamically-create-a-samba-account-if-needed.patch \
           file://400-samba-4.14-remove-getpwent_r.patch \
           file://401-cmocka-uintptr_t.patch \
           file://402-fix-finding-tools-heimdal-embedded.patch \
           file://500-SO_REUSEPORT-socket-option-nofail.patch \
           file://smb.conf \
           file://smb-user.conf \
           file://smb-branding.conf \
           file://smb-global.conf \
           file://smb-shares.conf \
           file://smb-vmc.samba \
           file://init.samba \
           file://init.wsdd \
           file://pam.samba \
           file://users.map \
           file://smbpasswd \
           file://volatiles.03_samba \
           "

SRC_URI:append:arm = " \
           file://500-arm-status-segfault-fix.patch \
          "

SRC_URI[sha256sum] = "2bbb800af2d6bc7b9c23e708cd58d008da3bfa6ef7e621d9ec8b53387efee445"
UPSTREAM_CHECK_REGEX = "samba\-(?P<pver>4\.19(\.\d+)+).tar.gz"
