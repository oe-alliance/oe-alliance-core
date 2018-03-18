SUMMARY = "WebSocket client for python. hybi13 is supported."
DESCRIPTION = "websocket-client module is WebSocket client for python. This provide the low level APIs for WebSocket. All APIs are the synchronous functions"
HOMEPAGE = "https://github.com/websocket-client/websocket-client.git"
LICENSE = "LGPL-3.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=4999ff812affd4e43e9848e8bfc732cb"

inherit pypi setuptools

PYPI_PACKAGE = "websocket_client"

PACKAGES = "${PN}"

SRC_URI[md5sum] = "790b3ecb5364293ad70c59a1b92debb1"
SRC_URI[sha256sum] = "a453dc4dfa6e0db3d8fd7738a308a88effe6240c59f3226eb93e8f020c216149"
