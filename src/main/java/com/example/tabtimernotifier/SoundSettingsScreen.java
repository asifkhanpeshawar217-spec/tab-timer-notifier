package com.example.tabtimernotifier;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import java.io.File;

public final class SoundSettingsScreen extends Screen {
 private final Screen parent;
 private SliderWidget nukeSlider, cloneSlider;
 public SoundSettingsScreen(Screen parent){super(Text.literal("Tab Timer Notifier"));this.parent=parent;}
 @Override protected void init(){
  int cx=width/2, top=Math.max(35,height/2-105), cardW=Math.min(430,width-30);
  int left=cx-cardW/2, half=(cardW-14)/2;
  addDrawableChild(ButtonWidget.builder(Text.literal("Upload MP3"),b->choose(true)).dimensions(left,top+40,half,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Preview"),b->IndependentAudio.preview(NotifierSettings.nukeFile,NotifierSettings.nukeVolume)).dimensions(left+half+14,top+40,half,20).build());
  nukeSlider=new VolumeSlider(left,top+67,cardW,20,NotifierSettings.nukeVolume,true);addDrawableChild(nukeSlider);
  addDrawableChild(ButtonWidget.builder(Text.literal("Restore Default"),b->{NotifierSettings.restoreNuke();nukeSlider.setValueFromSettings();}).dimensions(left,top+92,cardW,20).build());
  int y=top+139;
  addDrawableChild(ButtonWidget.builder(Text.literal("Upload MP3"),b->choose(false)).dimensions(left,y+40,half,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Preview"),b->IndependentAudio.preview(NotifierSettings.cloneFile,NotifierSettings.cloneVolume)).dimensions(left+half+14,y+40,half,20).build());
  cloneSlider=new VolumeSlider(left,y+67,cardW,20,NotifierSettings.cloneVolume,false);addDrawableChild(cloneSlider);
  addDrawableChild(ButtonWidget.builder(Text.literal("Restore Default"),b->{NotifierSettings.restoreClone();cloneSlider.setValueFromSettings();}).dimensions(left,y+92,cardW,20).build());
  addDrawableChild(ButtonWidget.builder(Text.literal("Done"),b->{client.setScreen(parent);}).dimensions(cx-50,Math.min(height-32,y+128),100,20).build());
 }
 private void choose(boolean nuke){
  JFileChooser chooser=new JFileChooser();chooser.setDialogTitle("Choose a notification sound (MP3 or WAV)");
  chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Audio files (MP3, WAV, AIFF)", "mp3","wav","aiff","aif"));
  if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){String path=chooser.getSelectedFile().getAbsolutePath();if(nuke)NotifierSettings.nukeFile=path;else NotifierSettings.cloneFile=path;NotifierSettings.save();}
 }
 @Override public void render(DrawContext ctx,int mouseX,int mouseY,float delta){
  renderBackground(ctx,mouseX,mouseY,delta);
  int cx=width/2, top=Math.max(35,height/2-105), cardW=Math.min(430,width-30), left=cx-cardW/2;
  ctx.fill(0,0,width,height,0xD9101420);
  ctx.fill(left-12,top-28,left+cardW+12,top+269,0xF0222938);
  ctx.fill(left-12,top-28,left+cardW+12,top-27,0xFF8B70E8);
  ctx.drawCenteredText(textRenderer,"TAB TIMER NOTIFIER",cx,top-18,0xFFFFFFFF);
  ctx.drawText(textRenderer,"NUKE NOTIFICATION",left,top+15,0xFFB9A7FF,false);
  ctx.drawText(textRenderer,"CLONE NOTIFICATION",left,top+114,0xFFB9A7FF,false);
  drawFileName(ctx,NotifierSettings.nukeFile,left,top+32);
  drawFileName(ctx,NotifierSettings.cloneFile,left,top+131);
  super.render(ctx,mouseX,mouseY,delta);
 }
 private void drawFileName(DrawContext ctx,String path,int x,int y){String s=path==null||path.isBlank()?"Built-in tone":new File(path).getName();ctx.drawText(textRenderer,"Sound: "+s,x,y,0xFFB8C2D8,false);}
 @Override public void close(){if(client!=null)client.setScreen(parent);}
 private final class VolumeSlider extends SliderWidget {
  private final boolean nuke;
  VolumeSlider(int x,int y,int w,int h,double value,boolean nuke){super(x,y,w,h,Text.literal("Volume"),value);this.nuke=nuke;updateMessage();}
  @Override protected void updateMessage(){setMessage(Text.literal("Volume: "+Math.round(value*100)+"%"));}
  @Override protected void applyValue(){if(nuke)NotifierSettings.nukeVolume=(float)value;else NotifierSettings.cloneVolume=(float)value;NotifierSettings.save();}
  void setValueFromSettings(){setValue(nuke?NotifierSettings.nukeVolume:NotifierSettings.cloneVolume);}
 }
}
