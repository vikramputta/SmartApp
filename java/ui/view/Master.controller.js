var javaPath = "/destinations/java/live3";

---

	handleStartDialogClose: function(oEvent) {
		if (oEvent.getSource().getId() === "ok") {
			var track = encodeURI(sap.ui.getCore().byId("iTrack").getValue());
			$.ajax({
				url: javaPath,
				type: "get",
				data: {
					track: track
				},
				error: function() {
					sap.m.MessageToast.show("Start error.");
				},
				success: function() {
					sap.m.MessageToast.show("Started.");
				}
			});
		}
		this.dlgStart.close();
	},

	handleStop: function() {
		$.ajax({
			url: javaPath,
			type: "get",
			data: {
				stop: "y"
			},
			error: function() {
				sap.m.MessageToast.show("Stop error.");
			},
			success: function() {
				sap.m.MessageToast.show("Stopped.");
			}
		});
	},
