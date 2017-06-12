Ext.define('MyApp.store.SudokuLists', {
    extend:'Ext.data.Store',
    model:'MyApp.model.SudokuList',
    autoLoad:false,

    proxy: {
        type: 'ajax',
        localUrl : 'solve/getSolution',
        remoteUrl : 'solve/getSolution',
    }
});