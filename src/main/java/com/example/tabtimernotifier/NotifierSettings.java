package com.example.tabtimernotifier;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
public final class NotifierSettings {
 private static final Path FILE=FabricLoader.getInstance().getConfigDir().resolve("tab-timer-notifier.json");
 public static String nukeFile="", cloneFile="";
 public static float nukeVolume=.85f, cloneVolume=.85f;
 private NotifierSettings(){}
 public static void load(){ if(!Files.exists(FILE))return; try(Reader r=Files.newBufferedReader(FILE)){JsonObject j=JsonParser.parseReader(r).getAsJsonObject(); nukeFile=j.has("nukeFile")?j.get("nukeFile").getAsString():""; cloneFile=j.has("cloneFile")?j.get("cloneFile").getAsString():""; nukeVolume=clamp(j.has("nukeVolume")?j.get("nukeVolume").getAsFloat():.85f); cloneVolume=clamp(j.has("cloneVolume")?j.get("cloneVolume").getAsFloat():.85f);}catch(Exception e){System.err.println("[Tab Timer Notifier] Settings load failed: "+e.getMessage());}}
 public static void save(){JsonObject j=new JsonObject();j.addProperty("nukeFile",nukeFile);j.addProperty("cloneFile",cloneFile);j.addProperty("nukeVolume",nukeVolume);j.addProperty("cloneVolume",cloneVolume);try{Files.createDirectories(FILE.getParent());try(Writer w=Files.newBufferedWriter(FILE)){new com.google.gson.GsonBuilder().setPrettyPrinting().create().toJson(j,w);}}catch(Exception e){System.err.println("[Tab Timer Notifier] Settings save failed: "+e.getMessage());}}
 public static float clamp(float v){return Math.max(0f,Math.min(1f,v));}
 public static void restoreNuke(){nukeFile="";nukeVolume=.85f;save();}
 public static void restoreClone(){cloneFile="";cloneVolume=.85f;save();}
}
