PV="4.10.17"
HOMEPAGE = "https://www.samba.org/"
SECTION = "console/network"

require conf/license/license-gplv2.inc
SAMBA_MIRROR = "http://samba.org/samba/ftp"
MIRRORS += "\
${SAMBA_MIRROR}    http://mirror.internode.on.net/pub/samba \n \
${SAMBA_MIRROR}    http://www.mirrorservice.org/sites/ftp.samba.org \n \
"

FILESEXTRAPATHS:prepend := "${THISDIR}/samba:"

SRC_URI = "${SAMBA_MIRROR}/stable/samba-${PV}.tar.gz \
           file://001-samba-4.9.15-pam.patch \
           file://002-dnsserver-4.9.15.patch \
           file://101-do-not-check-xsltproc-manpages.patch \
           file://103-tmsize-overflow-fix.patch \
           file://201-add-config-option-without-valgrind.patch \
           file://202-iconv-4.9.15.patch \
           file://203-netdb_defines.patch \
           file://207-glibc_only.patch \
           file://0001-waf-add-support-of-cross_compile.patch \
           file://0001-lib-replace-wscript-Avoid-generating-nested-main-fun.patch \
           file://0002-util_sec.c-Move-__thread-variable-to-global-scope.patch \
           file://0001-Add-options-to-configure-the-use-of-libbsd.patch \
           file://0001-nsswitch-nsstest.c-Avoid-nss-function-conflicts-with.patch \
           file://0001-util-Simplify-input-validation.patch \
           file://0002-util-Fix-build-on-FreeBSD-by-avoiding-NSS_BUFLEN_PAS.patch \
           file://0003-util-Reallocate-larger-buffer-if-getpwuid_r-returns-.patch \
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
SRC_URI:append:libc-musl = " \
           file://400-samba-4.10.10-remove-getpwent_r.patch \
           file://401-cmocka-uintptr_t.patch \
           file://402-samba-fix-musl-lib-without-innetgr.patch \
          "

SRC_URI[md5sum] = "f69cac9ba5035ee60257520a209a0a83"
SRC_URI[sha256sum] = "03dc9758e7bfa2faf7cdeb45b4d40997e2ee16a41e71996aa666bc069e70ba3e"

UPSTREAM_CHECK_REGEX = "samba\-(?P<pver>4\.10(\.\d+)+).tar.gz"
