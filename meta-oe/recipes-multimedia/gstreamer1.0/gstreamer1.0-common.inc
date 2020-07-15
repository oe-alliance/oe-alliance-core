GST_VERSION_FULL = "1.17.2.1"

inherit gitpkgv meson gettext

PV = "${GST_VERSION_FULL}+git${SRCPV}"
PKGV = "${GST_VERSION_FULL}+git${GITPKGV}"

SRCREV_gst = "cecb2912633164a66a942d5413404812ad8fc0a0"
SRCREV_gst_libav = "f7fc58112e668ef0718f8a09c95d32ba9cc38140"

SRCREV_gst_plugins_bad = "e7160a97cf0a3e8c94e173a01d0a0e82134dd4cb"
SRCREV_gst_plugins_base = "29497285d7228d6c5c9a704f434cc4c02f429e45"
SRCREV_gst_plugins_good = "5a7275220bd18cb60b8f91ccadf919554de68383"
SRCREV_gst_plugins_ugly = "de77207bfa57b8d884a4ac6f3dfe59badca92723"

S = "${WORKDIR}/git"
