FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

PV = "4.0.3"

SRC_URI[sha256sum] = "77964acc370d5c8375b9502e5ba6c13c03ef91ab9eb9f521c84fb42b9c9a6b0f"

SRC_URI:append = "file://a130f137f9208b0103a5382dbac714b0dbfce9e4.patch \
   file://b41b143018b5f4a2ef0a474d5b93f06fcfef539b.patch \
   file://b2ff380d5ba0c966b3bfcd2fd05afd0934bfd84a.patch \
"
