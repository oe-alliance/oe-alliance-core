FILESEXTRAPATHS:prepend := "${THISDIR}/${PN}:"

SRC_URI:append = "file://a130f137f9208b0103a5382dbac714b0dbfce9e4.patch \
   file://b41b143018b5f4a2ef0a474d5b93f06fcfef539b.patch \
   file://b2ff380d5ba0c966b3bfcd2fd05afd0934bfd84a.patch \
"
