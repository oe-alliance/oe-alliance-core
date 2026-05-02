FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

DEPENDS:append = " python3-semantic-version-native"

SRC_URI += "file://openssl40-opaque-asn1.patch"

SRC_URI:append:mips32el = " \
    crate://crates.io/portable-atomic/1.6.0 \
"

SRC_URI[portable-atomic-1.6.0.sha256sum] = "7170ef9988bc169ba16dd36a7fa041e5c4cbeb6a35b76d4c03daded371eae7c0"

PACKAGES =+ "${PN}-test"

include python3-package-split.inc

do_configure:prepend() {
    # OpenSSL 4.0 support: openssl-sys 0.9.110 rejects OpenSSL >= 4.0.
    for d in ${CARGO_HOME}/bitbake/openssl-sys-*; do
        if [ -d "$d" ]; then
            sed -i 's/if openssl_version >= 0x4_00_00_00_0 {/if false {/' $d/build/main.rs
            pkg=$(python3 -c "import json; print(json.load(open('$d/.cargo-checksum.json')).get('package',''))")
            echo "{\"files\":{},\"package\":\"$pkg\"}" > $d/.cargo-checksum.json
        fi
    done
}
