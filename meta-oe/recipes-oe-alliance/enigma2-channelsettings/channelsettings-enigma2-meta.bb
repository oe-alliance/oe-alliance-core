SUMMARY = "meta file for settings"

require conf/license/license-gplv2.inc

inherit allarch

PR = "r39"

DEPENDS = " \
    enigma2-plugin-settings-henksat-19e \
    enigma2-plugin-settings-henksat-23e \
    enigma2-plugin-settings-henksat-13e-19e \
    enigma2-plugin-settings-henksat-19e-23e \
    enigma2-plugin-settings-henksat-19e-23e-28e \
    enigma2-plugin-settings-henksat-13e-19e-23e-28e \
    enigma2-plugin-settings-henksat-wavefrontier \
    enigma2-plugin-settings-henksat-rotating \
    enigma2-plugin-settings-henksat-13e-19e-23e-28e-4.8e-0.8w \
    enigma2-plugin-settings-henksat-13e-19e-23e-28e-42e-7e \
    enigma2-plugin-settings-henksat-ziggo-1111 \
    enigma2-plugin-settings-hans-19e-23e \
    enigma2-plugin-settings-hans-19e-23e-28e \
    enigma2-plugin-settings-hans-13e-19e-23e-28e \
    enigma2-plugin-settings-hans-13e-19e-23e-28e-rotating \
    enigma2-plugin-settings-rytec-13e19e23e28e \
    enigma2-plugin-settings-rytec-motor \
    \
    enigma2-plugin-settings-catseye.23e.19e \
    enigma2-plugin-settings-catseye.5e.1w \
    enigma2-plugin-settings-catseye.7w \
    enigma2-plugin-settings-catseye.28e \
    enigma2-plugin-settings-catseye.75e.to.45w \
    enigma2-plugin-settings-cst.dual1.db \
    enigma2-plugin-settings-cst.dual2.db \
    enigma2-plugin-settings-cst.four3.db \
    enigma2-plugin-settings-cst.mono.db \
    enigma2-plugin-settings-cst.motor.db \
    enigma2-plugin-settings-cst.trial3.db \
    enigma2-plugin-settings-cst.trial5.db \
    enigma2-plugin-settings-malimali.9e.13e.19e \
    enigma2-plugin-settings-malimali.13e \
    enigma2-plugin-settings-malimali.13e.19e \
    enigma2-plugin-settings-malimali.19e \
    enigma2-plugin-settings-malimali.motor.42e.to.30w \
    enigma2-plugin-settings-matze-19e-13e \
    enigma2-plugin-settings-matze-19e \
    enigma2-plugin-settings-predrag.13e \
    enigma2-plugin-settings-predrag.19e \
    enigma2-plugin-settings-predrag.19e.13e \
    enigma2-plugin-settings-predrag.19e.16e.13e.08w \
    enigma2-plugin-settings-predrag.28e.19e.13e.0.8w \
    enigma2-plugin-settings-predrag.28e.23e.19e.16e.13e.7e.4.8e.0.8w.4.0w \
    enigma2-plugin-settings-predrag.28e.26e.23e.19e.16e.13e.7.0e.0.8w.4.0w \
    enigma2-plugin-settings-predrag.28e.30w \
    enigma2-plugin-settings-predrag.39e.28e.19e.16e.13e.0.8w \
    enigma2-plugin-settings-predrag.39e.28e.26e.19e.16e.13e.7.0e.0.8w \
    enigma2-plugin-settings-predrag.39e.28e.26e.23e.19e.16e.13e.7.0e.0.8w.4.0w \
    enigma2-plugin-settings-predrag.motor.42e.to.30w \
    enigma2-plugin-settings-vhannibal.dual.feeds \
    enigma2-plugin-settings-vhannibal.dual.feeds.dtt.italia \
    enigma2-plugin-settings-vhannibal.dual.feeds.dtt.milano \
    enigma2-plugin-settings-vhannibal.dual.feeds.dtt.napoli \
    enigma2-plugin-settings-vhannibal.dual.feeds.dtt.roma \
    enigma2-plugin-settings-vhannibal.dual.feeds.dtt.torino \
    enigma2-plugin-settings-vhannibal.hotbird \
    enigma2-plugin-settings-vhannibal.hotbird.dtt.italia \
    enigma2-plugin-settings-vhannibal.hotbird.dtt.milano \
    enigma2-plugin-settings-vhannibal.hotbird.dtt.napoli \
    enigma2-plugin-settings-vhannibal.hotbird.dtt.roma \
    enigma2-plugin-settings-vhannibal.hotbird.dtt.torino \
    enigma2-plugin-settings-vhannibal.motor \
    enigma2-plugin-settings-vhannibal.motor.and.dtt.forli \
    enigma2-plugin-settings-vhannibal.motor.and.dtt.italia \
    enigma2-plugin-settings-vhannibal.motor.and.dtt.milano \
    enigma2-plugin-settings-vhannibal.motor.and.dtt.napoli \
    enigma2-plugin-settings-vhannibal.motor.and.dtt.roma \
    enigma2-plugin-settings-vhannibal.motor.and.dtt.torino \
    enigma2-plugin-settings-vhannibal.quad.7e.13e.19e.42e \
    enigma2-plugin-settings-vhannibal.quad.9e.13e.16e.19e \
    enigma2-plugin-settings-vhannibal.quad.13e.19e.23e.28e \
    enigma2-plugin-settings-vhannibal.quad.nordic \
    enigma2-plugin-settings-vhannibal.trial.30w \
    \
    enigma2-plugin-settings-gigablue-19e \
    enigma2-plugin-settings-gigablue-19e-13e \
    enigma2-plugin-settings-gigablue-19e-13e-5e-75e \
    enigma2-plugin-settings-gigablue-19e-16e-13e \
    enigma2-plugin-settings-gigablue-42e-19e-13e-7e \
    enigma2-plugin-settings-gigablue-hepsidijital \
    \
    enigma2-plugin-settings-gigablue-wilhelmtel \
    enigma2-plugin-settings-gigablue-unity-media \
    enigma2-plugin-settings-gigablue-kabel-bw \
    enigma2-plugin-settings-gigablue-kabeldeutschland \
    "
