FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS:append = " python3-semantic-version-native"

SRC_URI:append:mips32el = " \
    crate://crates.io/portable-atomic/1.6.0 \
"

SRC_URI[portable-atomic-1.6.0.sha256sum] = "7170ef9988bc169ba16dd36a7fa041e5c4cbeb6a35b76d4c03daded371eae7c0"

include python3-package-split.inc
