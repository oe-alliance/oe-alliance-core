GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "0daa48f1b149b9c0689d216cc7d983975418bf4a"
SRCREV_gst_libav = "f29e9dfe9cc46e4e3b6340c782a1a0a479f9a400"

SRCREV_gst_plugins_bad = "3ce1086b14d63a7915f6de7149f5bcc8e509e4d5"
SRCREV_gst_plugins_base = "56e05f63f5815c72d831e646786a68afeb01960d"
SRCREV_gst_plugins_good = "6d4dcb430d5448f506bb9076c7abe19200a081c3"
SRCREV_gst_plugins_ugly = "a9105ad1e1fb8cbcf787c2a967697707eea405ed"

S = "${WORKDIR}/git"
