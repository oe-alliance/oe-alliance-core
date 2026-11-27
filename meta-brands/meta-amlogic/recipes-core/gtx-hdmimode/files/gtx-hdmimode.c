/* SPDX-License-Identifier: MIT */
#include <errno.h>
#include <fcntl.h>
#include <stdint.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <xf86drm.h>
#include <xf86drmMode.h>

static int property(int fd, uint32_t object, uint32_t type, const char *name,
		    uint32_t *id, uint64_t *value)
{
	drmModeObjectPropertiesPtr props = drmModeObjectGetProperties(fd, object, type);
	int result = -1;
	if (!props)
		return -1;
	for (unsigned i = 0; i < props->count_props; i++) {
		drmModePropertyPtr prop = drmModeGetProperty(fd, props->props[i]);
		if (!prop)
			continue;
		if (!strcmp(prop->name, name)) {
			*id = prop->prop_id;
			if (value)
				*value = props->prop_values[i];
			result = 0;
		}
		drmModeFreeProperty(prop);
		if (!result)
			break;
	}
	drmModeFreeObjectProperties(props);
	return result;
}

static int set(drmModeAtomicReqPtr req, int fd, uint32_t obj, uint32_t type,
	       const char *name, uint64_t value)
{
	uint32_t id;
	return property(fd, obj, type, name, &id, NULL) ||
		drmModeAtomicAddProperty(req, obj, id, value) < 0;
}

/* Indices are the Amlogic color_space connector enum. The driver validates
 * the requested timing and link bandwidth against the connected sink. */
static const char *const spaces[] = { "rgb", "422", "444", "420" };

static int valid_color(const char *mode, unsigned space, unsigned depth)
{
	char query[64];
	int length = snprintf(query, sizeof(query), "%s%s,%ubit", mode, spaces[space], depth);
	if (length < 0 || length >= (int)sizeof(query))
		return 0;
	int fd = open("/sys/class/amhdmitx/amhdmitx0/valid_mode", O_WRONLY | O_CLOEXEC);
	if (fd < 0)
		return 0;
	int valid = write(fd, query, length) == length;
	close(fd);
	return valid;
}

static int set_output(drmModeAtomicReqPtr req, int fd, uint32_t connector,
		      const char *mode, char **options)
{
	const uint32_t type = DRM_MODE_OBJECT_CONNECTOR;
	uint32_t id;
	uint64_t current_space, current_depth;
	if (property(fd, connector, type, "color_space", &id, &current_space) ||
	    property(fd, connector, type, "color_depth", &id, &current_depth))
		return -1;
	const unsigned order[] = { current_space < 4 ? (unsigned)current_space : 1, 1, 2, 3, 0 };
	const unsigned depths[] = { (unsigned)current_depth, 10, 8, 12 };
	const int auto_space = !strcmp(options[0], "auto");
	const int auto_depth = !strcmp(options[1], "auto");
	unsigned space = 6, depth = 0; /* RESERVED6 asks the driver to select the format. */
	for (unsigned i = 0; !(auto_space && auto_depth) && i < sizeof(order)/sizeof(*order) && !depth; i++) {
		unsigned candidate = order[i];
		if (!auto_space && strcmp(spaces[candidate], options[0]))
			continue;
		for (unsigned j = 0; j < sizeof(depths)/sizeof(*depths); j++) {
			unsigned bits = depths[j];
			if (bits != 8 && bits != 10 && bits != 12)
				continue;
			char label[8];
			snprintf(label, sizeof(label), "%ubit", bits);
			if ((auto_depth || !strcmp(label, options[1])) && valid_color(mode, candidate, bits)) {
				space = candidate;
				depth = bits;
				break;
			}
		}
	}
	if (!depth && !(auto_space && auto_depth))
		fprintf(stderr, "HDMI %s %s/%s unavailable, using automatic colour\n", mode, options[0], options[1]);
	return set(req, fd, connector, type, "color_space", space) ||
		(depth && set(req, fd, connector, type, "color_depth", depth)) ||
		set(req, fd, connector, type, "color_force", depth != 0) ||
		set(req, fd, connector, type, "FRAC_RATE_POLICY", !strcmp(options[2], "1")) ||
		set(req, fd, connector, type, "UPDATE", 1);
}

/* A retained boot frame uses the same video layer as live TV. Keep a
 * fullscreen layer fullscreen across modesets and preserve custom windows. */
static void resize_fullscreen_video(unsigned old_w, unsigned old_h, unsigned new_w, unsigned new_h)
{
	const char *path = "/sys/class/video/axis";
	int x, y, right, bottom;
	FILE *axis = fopen(path, "r");
	if (!axis)
		return;
	int full = fscanf(axis, "%d%d%d%d", &x, &y, &right, &bottom) == 4 &&
		!x && !y && right == (int)old_w - 1 && bottom == (int)old_h - 1;
	fclose(axis);
	if (!full)
		return;
	axis = fopen(path, "w");
	if (!axis)
		return;
	int written = fprintf(axis, "0 0 %u %u\n", new_w - 1, new_h - 1);
	if (fclose(axis) || written < 0)
		perror("GTX fullscreen video");
}

static int set_mode(int fd, drmModeResPtr res, drmModeConnectorPtr conn, const char *name,
		    char **options)
{
	drmModeModeInfoPtr mode = NULL;
	uint32_t crtc_id = 0, blob = 0;
	const int initial = !strcmp(name, "--start");
	int ret = -1;
	for (int i = 0; i < conn->count_modes; i++) {
		drmModeModeInfoPtr m = &conn->modes[i];
		if (!initial && !strcmp(m->name, name)) {
			mode = m;
			break;
		}
		if (initial && !(m->flags & DRM_MODE_FLAG_INTERLACE) && m->hdisplay <= 1920 &&
		    m->vdisplay <= 1080 && !strstr(m->name, "dummy") &&
		    (!mode || (m->type & DRM_MODE_TYPE_PREFERRED)))
			mode = m;
	}
	if (!mode) {
		fprintf(stderr, "HDMI mode %s unavailable\n", name);
		return -1;
	}

	for (int i = 0; !crtc_id && i < conn->count_encoders; i++) {
		drmModeEncoderPtr enc = drmModeGetEncoder(fd, conn->encoders[i]);
		if (!enc)
			continue;
		crtc_id = enc->crtc_id;
		for (int j = 0; !crtc_id && j < res->count_crtcs; j++)
			if (enc->possible_crtcs & (1U << j))
				crtc_id = res->crtcs[j];
		drmModeFreeEncoder(enc);
	}
	if (!crtc_id)
		return -1;
	drmModeCrtcPtr crtc = drmModeGetCrtc(fd, crtc_id);
	if (!crtc)
		return -1;
	drmModeAtomicReqPtr req = drmModeAtomicAlloc();
	drmModePlaneResPtr planes = drmModeGetPlaneResources(fd);
	if (!req || !planes || drmModeCreatePropertyBlob(fd, mode, sizeof(*mode), &blob))
		goto done;
	if (set(req, fd, conn->connector_id, DRM_MODE_OBJECT_CONNECTOR, "CRTC_ID", crtc_id) ||
	    set(req, fd, crtc_id, DRM_MODE_OBJECT_CRTC, "MODE_ID", blob) ||
	    set(req, fd, crtc_id, DRM_MODE_OBJECT_CRTC, "ACTIVE", 1) ||
	    (options && set_output(req, fd, conn->connector_id, name, options)))
		goto done;

	for (unsigned i = 0; i < planes->count_planes; i++) {
		if (!initial && crtc->mode_valid && crtc->mode.hdisplay == mode->hdisplay &&
		    crtc->mode.vdisplay == mode->vdisplay)
			break;
		drmModePlanePtr plane = drmModeGetPlane(fd, planes->planes[i]);
		if (!plane)
			goto done;
		uint32_t id = plane->plane_id, prop;
		uint64_t type;
		int primary = plane->crtc_id == crtc_id && plane->fb_id &&
			!property(fd, id, DRM_MODE_OBJECT_PLANE, "type", &prop, &type) &&
			type == DRM_PLANE_TYPE_PRIMARY;
		drmModeFreePlane(plane);
		if (!primary)
			continue;
		/* Preserve the current fb and source page. Enigma2 applies its saved
		 * calibration after switching modes. Start with the full output. */
		if (set(req, fd, id, DRM_MODE_OBJECT_PLANE, "CRTC_X", 0) ||
		    set(req, fd, id, DRM_MODE_OBJECT_PLANE, "CRTC_Y", 0) ||
		    set(req, fd, id, DRM_MODE_OBJECT_PLANE, "CRTC_W", mode->hdisplay) ||
		    set(req, fd, id, DRM_MODE_OBJECT_PLANE, "CRTC_H", mode->vdisplay))
			goto done;
	}
	ret = drmModeAtomicCommit(fd, req, DRM_MODE_ATOMIC_ALLOW_MODESET, NULL);
	if (ret)
		perror("GTX HDMI modeset");
	else if (crtc->mode_valid && (crtc->mode.hdisplay != mode->hdisplay ||
		 crtc->mode.vdisplay != mode->vdisplay))
		resize_fullscreen_video(crtc->mode.hdisplay, crtc->mode.vdisplay,
					mode->hdisplay, mode->vdisplay);
done:
	if (blob)
		drmModeDestroyPropertyBlob(fd, blob);
	if (planes)
		drmModeFreePlaneResources(planes);
	if (req)
		drmModeAtomicFree(req);
	drmModeFreeCrtc(crtc);
	return ret;
}

int main(int argc, char **argv)
{
	if (argc != 2 && argc != 5)
		return 2;
	int fd = open("/dev/dri/card0", O_RDWR | O_CLOEXEC);
	if (fd < 0 || drmSetClientCap(fd, DRM_CLIENT_CAP_ATOMIC, 1)) {
		perror("GTX DRM");
		return 1;
	}
	drmModeResPtr res = drmModeGetResources(fd);
	int result = -1;
	for (int i = 0; res && i < res->count_connectors; i++) {
		drmModeConnectorPtr conn = drmModeGetConnector(fd, res->connectors[i]);
		if (!conn)
			continue;
		if (conn->connector_type == DRM_MODE_CONNECTOR_HDMIA && conn->connection == DRM_MODE_CONNECTED) {
			result = set_mode(fd, res, conn, argv[1], argc == 5 ? &argv[2] : NULL);
			drmModeFreeConnector(conn);
			break;
		}
		drmModeFreeConnector(conn);
	}
	if (res)
		drmModeFreeResources(res);
	close(fd);
	return result ? 1 : 0;
}
