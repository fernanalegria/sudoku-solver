Ext.Loader.setConfig({enabled:true, disableCache:true});

Ext.application({
    name:'MyApp',
    appFolder:"app",
    autoCreateViewport:false,

    requires:['MyApp.Constants'],
    controllers:['MainController'],

    launch:function ()
    {
        this.viewport = Ext.create('MyApp.view.Main', {
            application:this
        });
    }
});