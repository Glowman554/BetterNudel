# -*- coding: utf-8 -*-
from __future__ import absolute_import, unicode_literals

import octoprint.plugin
import requests

# interesting events: PrinterStateChanged, Home

def api_request(plugin, url: str, event: str, payload: str):
	plugin._logger.info("-> url: {}, event: {}, payload: {}, token: {}".format(url, event, payload, plugin._settings.get(["token"])))

	req = requests.get("{}?event={}&payload={}&token={}".format(url, event, payload, plugin._settings.get(["token"])))
	plugin._logger.info("<- response: {}".format(req.text))

class DiscordRPCPlugin(octoprint.plugin.StartupPlugin, octoprint.plugin.SettingsPlugin, octoprint.plugin.ProgressPlugin, octoprint.plugin.EventHandlerPlugin):
	def on_after_startup(self):
		self._logger.info("Hello World!")

	def on_print_progress(self, storage, path, progress):
		api_request(self, self._settings.get(["url"]), "progress", path + ":" + str(progress))

	def on_event(self, event, payload):
		if event == "PrinterStateChanged":
			print(payload)
			api_request(self, self._settings.get(["url"]), "state", payload.get("state_id"))
		elif event == "Home":
			api_request(self, self._settings.get(["url"]), "home", "-")

	def get_settings_defaults(self):
		return dict(url="https://x.glowman554.gq/api/v2/octoprint", token="-")

__plugin_name__ = "Discord RPC"
__plugin_version__ = "1.0.0"
__plugin_description__ = "Connect octoprint to discord!"
__plugin_pythoncompat__ = ">=2.7,<4"
__plugin_implementation__ = DiscordRPCPlugin()
