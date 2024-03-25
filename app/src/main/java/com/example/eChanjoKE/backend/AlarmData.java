/*
Copyright (C) 2024  Wrichik Basu (basulabs.developer@gmail.com)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published
by the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/
package com.example.eChanjoKE.backend;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class AlarmData {

	private boolean isSwitchedOn;
	private LocalDateTime alarmDateTime;
	private LocalTime alarmTime;
	private int alarmType;
	private boolean isRepeatOn;
	private ArrayList<Integer> repeatDays;
	private String alarmMessage;

	public AlarmData(boolean isSwitchedOn, @NonNull LocalDateTime alarmDateTime,
		int alarmType, @Nullable String alarmMessage) {
		this.isSwitchedOn = isSwitchedOn;
		this.alarmDateTime = alarmDateTime;
		this.alarmType = alarmType;
		this.isRepeatOn = false;
		this.repeatDays = null;
		this.alarmTime = alarmDateTime.toLocalTime();
		this.alarmMessage = alarmMessage;
	}


	public AlarmData(boolean isSwitchedOn, @NonNull LocalTime alarmTime, int alarmType,
		@Nullable String alarmMessage,
		@NonNull ArrayList<Integer> repeatDays) {
		this.isSwitchedOn = isSwitchedOn;
		this.alarmTime = alarmTime;
		this.alarmType = alarmType;
		this.isRepeatOn = true;
		this.repeatDays = repeatDays;
		this.alarmMessage = alarmMessage;
		this.alarmDateTime = null;
	}


	public boolean isRepeatOn() {
		return isRepeatOn;
	}


	public void setRepeatOn(boolean repeatOn) {
		isRepeatOn = repeatOn;
	}


	@Nullable
	public ArrayList<Integer> getRepeatDays() {
		return repeatDays;
	}

	public void setRepeatDays(@Nullable ArrayList<Integer> repeatDays) {
		this.repeatDays = repeatDays;
	}


	public boolean isSwitchedOn() {
		return isSwitchedOn;
	}


	public void setSwitchedOn(boolean switchedOn) {
		isSwitchedOn = switchedOn;
	}


	@Nullable
	public LocalDateTime getAlarmDateTime() {
		return alarmDateTime;
	}

	public void setAlarmDateTime(@Nullable LocalDateTime alarmDateTime) {
		this.alarmDateTime = alarmDateTime;
	}

	@NonNull
	public LocalTime getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(@NonNull LocalTime alarmTime) {
		this.alarmTime = alarmTime;
	}


	public int getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}


	public void setAlarmMessage(@Nullable String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	@Nullable
	public String getAlarmMessage() {
		return alarmMessage;
	}

	@NonNull
	@Override
	public String toString() {
		return "Alarm date time = " + alarmDateTime.toString();
	}
}
