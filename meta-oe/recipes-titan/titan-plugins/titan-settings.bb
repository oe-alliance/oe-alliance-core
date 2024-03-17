SUMMARY = "meta file for Titan Settings"
inherit packagegroup

require conf/license/license-gplv2.inc

DEPENDS = "\
  titan-plugin-settings-andy \
  titan-plugin-settings-bulldog.192.sat \
  titan-plugin-settings-bulldog.2sat.sat \
  titan-plugin-settings-bulldog.sat \
  titan-plugin-settings-bulldog.3xsat.unitymedia.sat \
  titan-plugin-settings-default.cable \
  titan-plugin-settings-default \
  titan-plugin-settings-default.all \
  titan-plugin-settings-kabelplus.cable \
  titan-plugin-settings-matze70.neue.design.sat \
  titan-plugin-settings-matze70.sat \
  titan-plugin-settings-orf.sat \
  titan-plugin-settings-paule.cable \
  titan-plugin-settings-phillips.cable \
  titan-plugin-settings-primacom.cable \
  titan-plugin-settings-ru.default \
  titan-plugin-settings-ser6.eutelsat36.sat \
  titan-plugin-settings-bw.cable \
  titan-plugin-settings-uljanow.astra \
  titan-plugin-settings-unitymedia.cable \
"

PR = "r0"
