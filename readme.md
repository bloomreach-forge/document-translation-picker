# Document Translation Picker Plugin for Hippo CMS
Purpose of this plugin is to couple existing documents as translations of each other. 

## Configuration

Import the xml shown below at /hippo:configuration/hippo:frontend to add the plugin to the submenu called 'Document'. Every document now has an item in the submenu 'Document'. Below other items as 'Rename...', 'Move...' etc there should now be an item named 'Select translations...'.
```xml
<?xml version="1.0" encoding="UTF-8"?><sv:node xmlns:sv="http://www.jcp.org/jcr/sv/1.0" xmlns:esv="http://www.onehippo.org/jcr/xmlimport" sv:name="default" esv:merge="combine">
  <sv:node sv:name="handle" esv:merge="combine">
    <sv:node sv:name="frontend:renderer" esv:merge="combine">
      <sv:node sv:name="documenttranslation">
        <sv:property sv:name="jcr:primaryType" sv:type="Name">
          <sv:value>frontend:plugin</sv:value>
        </sv:property>
        <sv:property sv:name="plugin.class" sv:type="String">
          <sv:value>nl.finalist.hippo.plugins.documenttranslationpicker.DocumentTranslationPickerPlugin</sv:value>
        </sv:property>
        <sv:property sv:name="wicket.id" sv:type="String">
          <sv:value>${item}</sv:value>
        </sv:property>
      </sv:node>
    </sv:node>
  </sv:node>
</sv:node>
```

## Usage
1. Make sure the folder which contains the document has translations in other languages. To do this, open the contextmenu of the folder and select 'Translations...'.
2. Open the document
3. Click on 'Document'in the toolbar. This will open a submenu.
4. Click on 'Select translations...' in the submenu. A dialog will be shown which lists documents of the same type in folders which are marked as translations of the folder containing the current document.
5. Select the documents which should be the translations.
6. Click OK

