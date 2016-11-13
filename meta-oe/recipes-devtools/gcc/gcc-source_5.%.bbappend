# Remove support for building musl configuration on sh4
SRC_URI_remove_sh4 = "\
    file://0037-aarch64-Add-support-for-musl-ldso.patch \
    file://0043-libstdc-Support-musl.patch \
    file://0044-Adding-mmusl-as-a-musl-libc-specifier-and-the-necess.patch \
    file://0045-Support-for-arm-linux-musl.patch \
    file://0046-Get-rid-of-ever-broken-fixincludes-on-musl.patch \
"

