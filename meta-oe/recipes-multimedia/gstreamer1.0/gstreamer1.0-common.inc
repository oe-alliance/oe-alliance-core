GST_VERSION_FULL = "1.19.0.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "77ab7b42b382d07982ca8df464e82949dd749612"
SRCREV_gst_libav = "672f1caf85493a830e9a81af974e6386580031a6"

SRCREV_gst_plugins_bad = "4b47b96ae16493b83e3995b436fb6cc737b56afa"
SRCREV_gst_plugins_base = "403e0024f11cec97d5431b27addef0ce6831205b"
SRCREV_gst_plugins_good = "d75c67847966ea25a0fb42194fb196a2a2a4e2b6"
SRCREV_gst_plugins_ugly = "4d4b82ffdc625be5269cfea2f35b85afbe406e8c"

S = "${WORKDIR}/git"
