GST_VERSION_FULL = "1.17.2.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "411d255154960e7f0923b2581e57180412c217f6"
SRCREV_gst_libav = "edce08b526d9288ea75b0b3e0597030c8714d4cf"

SRCREV_gst_plugins_bad = "fc9a612e2cb6a9d66319d316edce71bb058022cd"
SRCREV_gst_plugins_base = "f5a02639e1ae0f8e828d47a8788d4fc886265782"
SRCREV_gst_plugins_good = "d997a8d48bc3f0d6871e6c46a55f18458dee10d9"
SRCREV_gst_plugins_ugly = "ea945eb9c6af89a1ab70ad254fd6f9523eb5861d"

S = "${WORKDIR}/git"
