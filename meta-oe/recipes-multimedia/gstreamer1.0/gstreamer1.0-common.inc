GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "3393b456e47f634cf571c647285611adf34a6d9f"
SRCREV_gst_libav = "72933810f5dcd0e5ca401d2f3bfe3c2a638fb5c4"

SRCREV_gst_plugins_bad = "9443bf7a225a2cd3462b3b8a597bda46ee7092e3"
SRCREV_gst_plugins_base = "6643e941beb5a5cc00e220e841d2cb90fa2d8330"
SRCREV_gst_plugins_good = "64e89e43aa348194c025780a0593982ba8aa000b"
SRCREV_gst_plugins_ugly = "aaa4acb2dd9772c2032e2b0b3f9866b9820605a5"

S = "${WORKDIR}/git"
