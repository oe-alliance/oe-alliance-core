SUMMARY = "Accurately separates a URL's subdomain, domain, and public suffix, using the Public Suffix List (PSL). By default, this includes the public ICANN TLDs and their exceptions. You can optionally support the Public Suffix List's private domains as well."
HOMEPAGE = "https://github.com/john-kurkowski/tldextract"
SECTION = "devel/python"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0c87f552b1a2bb24e6955f5f56249cf1"

DEPENDS += "python3-setuptools-scm-native"

RDEPENDS:${PN} = "python3-idna python3-requests python3-requests-file python3-filelock"

SRC_URI[md5sum] = "e4e429649a5567af70c86669d5b7b9d4"
SRC_URI[sha256sum] = "a72756ca170b2510315076383ea2993478f7da6f897eef1f4a5400735d5057fb"

inherit pypi python_setuptools_build_meta

include python3-package-split.inc
