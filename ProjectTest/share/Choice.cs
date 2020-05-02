using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.Text;

namespace share
{
    public class Choice
    {

        public string message;

        public Choice()
        {

        }

        public override string ToString()
        {
            JObject obj = JObject.FromObject(this);
            return obj.ToString();
        }

    }

}
