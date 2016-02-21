	onRouteMatched : function(oEvent) {
		var oParameters = oEvent.getParameters();
		var oView = this.getView();

		if (oParameters.name === "master2") {
			var sEntityPath = "/" + oParameters.arguments.entity;
			this.bindView(sEntityPath);

			var oEventBus = this.getEventBus();
			var that = this;
			this.byId("master2List").attachUpdateFinished(function(){
			//oView.getModel().attachRequestCompleted(function(){
			//	if(jQuery("#" + that.getView().getId()).is(':visible')){
    			    that.selectFirstItem();
                	oEventBus.publish("Master2", "LoadFinished",{
                		oListItem: that.getView().byId("master2List").getItems()[0]
                	});
			//	}
			});
		}
