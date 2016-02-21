jQuery.sap.require("sap.m.MessageToast");
var servicesPath = "/destinations/hanaxs/p000trial/dev/smartapp/";

	onSearch : function() {
		// Add search filter
		var filters = [];
		var searchString = this.getView().byId("master1SearchField").getValue();
		if (searchString && searchString.length > 0) {
			filters = [ new sap.ui.model.Filter("clusterNumber", sap.ui.model.FilterOperator.EQ, searchString) ];
		}

		// Update list binding
		this.getView().byId("master1List").getBinding("items").filter(filters);
	},

	handleRefresh: function() {
		$.ajax({
			url: servicesPath + "services.xsodata/Tweets/$count",
			type: "get",
			error: function() {},
			success: function(data) {
				jQuery.sap.require("sap.m.MessageToast");
				sap.m.MessageToast.show("Tweets: " + data, {
					duration: 1000,
					my: "center top",
					at: "center top"
				});
			}
		});
		this.getView().getModel().refresh();
		this.getView().byId("pullToRefresh").hide();
	},

	handleStartDialog: function() {
		if (!this.dlgStart) {
			this.dlgStart = sap.ui.xmlfragment("smartapp.view.Start", this);
			this.getView().addDependent(this.dlgStart);
		}
		this.dlgStart.open();
	},

	handleStartDialogClose: function(oEvent) {
		if (oEvent.getSource().getId() === "ok") {
			var track = encodeURI(sap.ui.getCore().byId("iTrack").getValue());
			$.ajax({
				url: "/destinations/nodejs/do/start",
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
			url: "/destinations/nodejs/do/stop",
			type: "get",
			error: function() {
				sap.m.MessageToast.show("Stop error.");
			},
			success: function() {
				sap.m.MessageToast.show("Stopped.");
			}
		});
	},

	handleCluster: function() {
		$.ajax({
			url: servicesPath + "services.xsjs?cmd=cluster",
			type: "get",
			async: "false",
			error: function() {
				sap.m.MessageToast.show("Clustering error.");
			},
			success: function() {
				sap.m.MessageToast.show("Clustering finished.");
			}
		});
		this.getView().getModel().refresh();
	},

	handleReset: function() {
		$.ajax({
			url: servicesPath + "services.xsjs?cmd=reset",
			type: "get",
			async: "false",
			error: function() {
				sap.m.MessageToast.show("Reset error.");
			},
			success: function() {
				sap.m.MessageToast.show("Reset finished.");
			}
		});
		this.getView().getModel().refresh();
	},
