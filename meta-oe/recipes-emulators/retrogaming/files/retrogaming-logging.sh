# Shared by the in-process and full-Enigma2-handoff launchers.
# Read settings as data, never as shell code. Missing entries are E2 defaults.
retro_setting() {
    [ -r /etc/enigma2/settings ] || return 0
    awk -v key="$1=" 'index($0, key) == 1 { value = substr($0, length(key) + 1) } END { print value }' /etc/enigma2/settings
}

RETRO_DEBUG=false
RETRO_LOG_PREFIX=""
if [ "$(retro_setting config.plugins.retrogaming.debug)" = True ]; then
    RETRO_DEBUG=true
    RETRO_LOG_PREFIX="RetroGaming-"
    RETRO_DEBUG_DIR="$(retro_setting config.crash.debug_path)"
    RETRO_DEBUG_DIR="${RETRO_DEBUG_DIR:-/home/root/logs/}"
    RETRO_DEBUG_AVAILABLE=true
    case "${RETRO_DEBUG_DIR}" in
        /*) ;;
        *) RETRO_DEBUG_AVAILABLE=false ;;
    esac
    RETRO_DEBUG_DIR="$(readlink -f "${RETRO_DEBUG_DIR}" 2>/dev/null || true)"
    [ -n "${RETRO_DEBUG_DIR}" ] || RETRO_DEBUG_AVAILABLE=false
    case "${RETRO_DEBUG_DIR}" in
        /media/*)
            # Never create a log directory on flash below an unmounted disk.
            awk -v path="${RETRO_DEBUG_DIR}" '
                $2 != "/" && $2 != "/media" {
                    mount = $2
                    gsub(/\\040/, " ", mount)
                    if (path == mount || index(path, mount "/") == 1) found = 1
                }
                END { exit !found }
            ' /proc/mounts || RETRO_DEBUG_AVAILABLE=false
            ;;
    esac
    if [ "${RETRO_DEBUG_AVAILABLE}" = true ] &&
       mkdir -p "${RETRO_DEBUG_DIR}" && [ -w "${RETRO_DEBUG_DIR}" ]; then
        RETRO_LOG_DIR="${RETRO_DEBUG_DIR}"
    else
        echo "RetroGaming: Enigma2 log storage unavailable; using /tmp/retrogaming/logs." >&2
        RETRO_LOG_DIR="/tmp/retrogaming/logs"
    fi
fi
mkdir -p "${RETRO_LOG_DIR}"
RETRO_ARCH_LOG="${RETRO_LOG_DIR}/${RETRO_LOG_PREFIX}retroarch.log"
RETRO_PLATFORM_LOG="${RETRO_LOG_DIR}/${RETRO_LOG_PREFIX}platform.log"
RETRO_EXIT_LOG="${RETRO_LOG_DIR}/${RETRO_LOG_PREFIX}exit-monitor.log"

retro_prepare_log() {
    [ "${RETRO_DEBUG}" = true ] || return 0
    # Keep the previous run, not an unlimited set of timestamped logs.
    if [ -f "$1" ]; then
        mv -f "$1" "${1%.log}.previous.log"
    fi
    (umask 077; : > "$1")
}

retro_debug_start() {
    [ "${RETRO_DEBUG}" = true ] || return 0
    RETRO_LAUNCH_LOG="${RETRO_LOG_DIR}/RetroGaming-$1.log"
    retro_prepare_log "${RETRO_LAUNCH_LOG}" || return 0
    exec >>"${RETRO_LAUNCH_LOG}" 2>&1
    printf 'RetroGaming diagnostics: %s\n' "$(date '+%Y-%m-%d %H:%M:%S')"
    printf 'Log directory: %s\n' "${RETRO_LOG_DIR}"
    uname -a
    for RETRO_INFO in /proc/stb/info/model /proc/stb/info/chipset /proc/stb/video/videomode /proc/asound/cards; do
        if [ -r "${RETRO_INFO}" ]; then
            printf '\n%s:\n' "${RETRO_INFO}"
            cat "${RETRO_INFO}"
        fi
    done
}
